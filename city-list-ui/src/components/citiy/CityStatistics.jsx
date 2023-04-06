import React, { useState, useEffect } from 'react';
import {
  Menu,
} from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import { getNumberOfCities } from '../../cityapi/citiesApi';
import { isAuthenticated } from '../../cityapi/userContextUtils';

function CityStatistics() {
  const [numberOfCities, setNumberOfCities] = useState(null);
  const { t } = useTranslation('common');

  useEffect(() => {
    if (isAuthenticated() && !numberOfCities) {
      getNumberOfCities().then((response) => {
        if (response && response.data) {
          setNumberOfCities(response.data);
        }
      });
    }
  }, [numberOfCities]);

  if (isAuthenticated()) {
    return (
      <Menu.Item header>
        {t('city.stat.infoText')}
        {numberOfCities}
      </Menu.Item>
    );
  }

  return null;
}

export default CityStatistics;
