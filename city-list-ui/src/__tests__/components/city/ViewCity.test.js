import React from 'react';
import { render, screen } from '@testing-library/react';
import ViewCity from '../../../components/citiy/ViewCity';

describe('ViewCity', () => {
  const city = {
    name: 'New York',
    photo: 'https://example.com/photo.jpg',
  };

  it('renders without crashing', () => {
    render(<ViewCity city={city} handleClose={() => {}} openModal />);
  });

  it('displays the city name in the modal header', () => {
    render(<ViewCity city={city} handleClose={() => {}} openModal />);
    expect(screen.getByText('New York')).toBeInTheDocument();
  });

  it('displays the city photo in the modal content', () => {
    render(<ViewCity city={city} handleClose={() => {}} openModal />);
    expect(screen.getByRole('img')).toHaveAttribute('src', 'https://example.com/photo.jpg');
  });
});
