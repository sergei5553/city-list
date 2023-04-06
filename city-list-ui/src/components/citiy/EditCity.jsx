import React, { useState } from 'react';
import {
  Modal, Form, Button, TextArea, Message,
} from 'semantic-ui-react';
import PropTypes from 'prop-types';
import { useTranslation } from 'react-i18next';
import { updateCity } from '../../cityapi/citiesApi';
import CustomImage from '../common/CustomImage';

function EditCity({
  openModal, city, handleClose, onEditComplete,
}) {
  const [error, setError] = useState(null);
  const [newCityName, setNewCityName] = useState(city.name);
  const [newImageURL, setNewImageURL] = useState(city.photo);
  const [loading, setLoading] = useState(false);
  const { t } = useTranslation('common');

  const handleNewCityNameChange = (e, { value }) => {
    setNewCityName(value);
  };

  const handleImageURLChange = (e, { value }) => {
    setNewImageURL(value);
  };

  const handleCloseModal = () => {
    setError(false);
    handleClose();
  };

  const validate = () => {
    if (newImageURL || newCityName) {
      setError(null);
      return true;
    }
    setError(t('city.edit.validationError'));
    return false;
  };

  const handleSave = () => {
    if (validate()) {
      setLoading(true);
      updateCity({ id: city.id, name: newCityName, photo: newImageURL })
        .then((response) => {
          if (response && response.status === 200) {
            handleCloseModal();
            onEditComplete(new Date().getTime());
          } else {
            setError(t('city.edit.updateError'));
          }
          setLoading(false);
        }).catch(() => {
          setError(t('city.edit.updateError'));
        }).finally(() => {
          setLoading(false);
        });
    }
  };

  return (

    <Modal
      open={openModal}
      onClose={handleCloseModal}
      closeOnEscape
      closeOnRootNodeClick
    >
      {error && <Message negative>{error}</Message>}
      <Modal.Header>{newCityName || city.name}</Modal.Header>
      <Modal.Content image>
        <CustomImage src={newImageURL || city.photo} size="large" wrapped />
        <Modal.Description style={{ width: '50%' }}>
          <Form>
            <TextArea
              onChange={handleNewCityNameChange}
              defaultValue={city.name}
              placeholder={t('city.edit.namePlaceHolder')}
            />
            <p />
            <TextArea
              rows={5}
              onChange={handleImageURLChange}
              defaultValue={city.photo}
              placeholder={t('city.edit.URLPlaceHolder')}
            />
          </Form>
        </Modal.Description>
      </Modal.Content>
      <Modal.Actions>
        <Button content={t('city.edit.closeButton')} color="black" onClick={handleCloseModal} />
        <Button
          loading={loading}
          content={t('city.edit.saveButton')}
          labelPosition="right"
          icon="checkmark"
          onClick={handleSave}
          positive
        />
      </Modal.Actions>
    </Modal>
  );
}

EditCity.propTypes = {
  city: PropTypes.shape.isRequired,
  handleClose: PropTypes.func.isRequired,
  onEditComplete: PropTypes.func.isRequired,
  openModal: PropTypes.bool.isRequired,
};

export default EditCity;
