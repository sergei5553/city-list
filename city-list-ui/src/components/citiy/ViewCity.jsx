import React from 'react';
import { Modal } from 'semantic-ui-react';
import PropTypes from 'prop-types';
import CustomImage from '../common/CustomImage';

function ViewCity({ openModal, city, handleClose }) {
  return (
    <Modal size="tiny" open={openModal} onClose={handleClose} closeOnEscape closeIcon>
      <Modal.Header>{city.name}</Modal.Header>
      <Modal.Content image>
        <CustomImage src={city.photo} wrapped size="big" />
      </Modal.Content>
    </Modal>
  );
}

ViewCity.propTypes = {
  city: PropTypes.shape.isRequired,
  handleClose: PropTypes.func.isRequired,
  openModal: PropTypes.bool.isRequired,
};

export default ViewCity;
