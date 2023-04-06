import React, { useState } from 'react';
import { Pagination } from 'semantic-ui-react';
import PropTypes from 'prop-types';

function PaginationPanel({ totalPages, pageNumber, onPageChange }) {
  const [page, setPage] = useState(pageNumber);
  const handlePaginationChange = (e, { activePage }) => {
    setPage(activePage);
    onPageChange(activePage);
  };

  return (
    <Pagination
      activePage={page}
      onPageChange={handlePaginationChange}
      totalPages={totalPages}
    />

  );
}

PaginationPanel.propTypes = {
  totalPages: PropTypes.number.isRequired,
  pageNumber: PropTypes.number.isRequired,
  onPageChange: PropTypes.func.isRequired,
};

export default PaginationPanel;
