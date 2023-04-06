import React from 'react';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import ItemImage from '../../../components/common/ItemImage';

describe('ItemImage', () => {
  const src = 'https://example.com/image.png';
  const onClick = jest.fn();

  beforeEach(() => {
    onClick.mockClear();
  });

  it('renders an image with the given src', () => {
    render(<ItemImage src={src} onClick={onClick} />);
    const image = screen.getByRole('img');
    expect(image).toHaveAttribute('src', src);
  });

  it('calls the onClick handler when the image is clicked', () => {
    render(<ItemImage src={src} onClick={onClick} />);
    const image = screen.getByRole('img');
    userEvent.click(image);
    expect(onClick).toHaveBeenCalledTimes(1);
  });

  it('has the required propTypes', () => {
    const { propTypes } = ItemImage;
    expect(propTypes.src).toEqual(expect.any(Function));
    expect(propTypes.onClick).toEqual(expect.any(Function));
  });
});
