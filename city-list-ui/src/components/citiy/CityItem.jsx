import React, { useState } from 'react';
import { Item, Button, Icon } from 'semantic-ui-react';
import PropTypes from 'prop-types';
import ItemImage from '../common/ItemImage';
import ViewCity from './ViewCity';
import EditCity from './EditCity';
import { isFullAccessUser } from '../../cityapi/userContextUtils';
import classes from './CityItem.module.css';

function CityItem({ city, onEditComplete }) {
  const [openViewPopup, setOpenViewPopup] = useState(false);
  const [openEditPopup, setOpenEditPopup] = useState(false);

  const handleCloseViewPopup = () => setOpenViewPopup(false);
  const handleOpenViewPopup = () => setOpenViewPopup(true);

  const handleCloseEditPopup = () => setOpenEditPopup(false);
  const handleOpenEditPopup = () => setOpenEditPopup(true);

  return (
    <Item key={city.id} className={classes.item}>
      <ItemImage onClick={handleOpenViewPopup} src={city.photo} />
      <Item.Content verticalAlign="middle">
        <Item.Header onClick={handleOpenViewPopup} content={city.name} />
      </Item.Content>
      {isFullAccessUser()
                && (
                <Item.Extra>
                  <Button onClick={handleOpenEditPopup} icon floated="right">
                    <Icon name="edit" />
                  </Button>
                </Item.Extra>
                )}
      <ViewCity handleClose={handleCloseViewPopup} openModal={openViewPopup} city={city} />
      <EditCity
        handleClose={handleCloseEditPopup}
        openModal={openEditPopup}
        city={city}
        onEditComplete={onEditComplete}
      />
    </Item>
  );
}

CityItem.propTypes = {
  city: PropTypes.shape.isRequired,
  onEditComplete: PropTypes.func.isRequired,
};

export default CityItem;
