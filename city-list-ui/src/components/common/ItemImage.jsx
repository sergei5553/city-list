import React from 'react';

import PropTypes from 'prop-types';

import CustomImage from './CustomImage';

function ItemImage({ src, onClick }) {
  return (
    <CustomImage onClick={onClick} src={src} size="tiny" bordered rounded />
  );
}

ItemImage.propTypes = {
  src: PropTypes.string.isRequired,
  onClick: PropTypes.func.isRequired,
};

export default ItemImage;
