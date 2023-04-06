import React, { useEffect, useState } from 'react';
import { Image as SemanticImage } from 'semantic-ui-react';
import PropTypes from 'prop-types';
import { config } from '../../Constants';

function CustomImage({
  src, onClick, size, bordered, rounded, wrapped,
}) {
  const [imageExists, setImageExists] = useState(true);

  useEffect(() => {
    const img = new Image();
    img.onload = () => {
      setImageExists(true);
    };
    img.onerror = () => {
      setImageExists(false);
    };
    img.src = src;
  }, [src]);

  if (imageExists) {
    return (
      <SemanticImage
        onClick={onClick}
        src={src}
        size={size}
        bordered={bordered}
        rounded={rounded}
        wrapped={wrapped}
      />
    );
  }

  return (
    <SemanticImage
      onClick={onClick}
      src={config.image.NOT_EXIST}
      size={size}
      bordered={bordered}
      rounded={rounded}
      wrapped={wrapped}
    />
  );
}

CustomImage.defaultProps = {
  bordered: false,
  rounded: false,
  wrapped: false,
};

CustomImage.propTypes = {
  src: PropTypes.string.isRequired,
  onClick: PropTypes.func.isRequired,
  size: PropTypes.string.isRequired,
  bordered: PropTypes.bool,
  rounded: PropTypes.bool,
  wrapped: PropTypes.bool,
};

export default CustomImage;
