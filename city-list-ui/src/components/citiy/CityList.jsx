import React from 'react';
import PropTypes from 'prop-types';
import { Item } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import CityItem from './CityItem';

function CityList({ cities, onEditComplete }) {
  const { t } = useTranslation('common');

  let cityList;
  if (cities.length === 0) {
    cityList = <Item key="no-city">{t('city.list.emptyList')}</Item>;
  } else {
    cityList = cities
      .map((city) => (
        <CityItem key={city.id} city={city} onEditComplete={onEditComplete} />
      ));
  }

  return (
    <Item.Group divided unstackable relaxed link>
      {cityList}
    </Item.Group>
  );
}

CityList.propTypes = {
  cities: PropTypes.shape.isRequired,
  onEditComplete: PropTypes.func.isRequired,
};

export default CityList;
