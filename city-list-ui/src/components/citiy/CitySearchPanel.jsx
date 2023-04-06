import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { Input, Button, Icon } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';

function CitySearchPanel({ onChange }) {
  const { t } = useTranslation('common');
  const [searchText, setSearchText] = useState('');

  const handleSearch = () => {
    if (onChange) {
      onChange(searchText);
    }
  };

  const handleInputChange = ((e, { value }) => {
    setSearchText(value);
  });

  const handleClearSearch = () => {
    if (onChange) {
      onChange('');
      setSearchText('');
    }
  };

  function handleKeyDown(event) {
    if (event.key === 'Enter') {
      handleSearch();
    }
  }

  return (
    <Input
      action={{ icon: 'close', onClick: handleClearSearch }}
      name="cityTextSearch"
      placeholder={t('city.search.placeholder')}
      onChange={handleInputChange}
      value={searchText}
    >
      <input onKeyDown={handleKeyDown} />
      {searchText && (
      <Button color="orange" icon onClick={handleClearSearch}>
        <Icon name="close" />
      </Button>
      )}
      <Button icon onClick={handleSearch} type="submit">
        <Icon name="search" />
      </Button>
    </Input>
  );
}

CitySearchPanel.propTypes = {
  onChange: PropTypes.func.isRequired,
};

export default CitySearchPanel;
