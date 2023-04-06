import React, { useEffect, useState } from 'react';
import {
  Container, Grid, Header, Icon, Message, Segment,
} from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import CitySearchPanel from '../citiy/CitySearchPanel';
import CityList from '../citiy/CityList';
import { getCities } from '../../cityapi/citiesApi';
import PaginationPanel from './PaginationPanel';

function MainPage() {
  const [loading, setLoading] = useState(false);
  const [cities, setCities] = useState([]);
  const [cityName, setCityName] = useState('');
  const [totalPages, setTotalPages] = useState(10);
  const [pageNumber, setPageNumber] = useState(1);
  const [editComplete, setEditComplete] = useState(null);
  const [error, setError] = useState(null);
  const { t } = useTranslation('common');

  useEffect(() => {
    setLoading(true);
    setError(null);
    getCities(cityName, pageNumber ? pageNumber - 1 : 0).then((response) => {
      if (response && response.data) {
        setCities(response.data.cities);
        setTotalPages(Math.ceil(response.data.total / response.data.pageSize));
        setPageNumber(response.data.pageNumber + 1);
      }
      setLoading(false);
    }).catch(() => {
      setError(t('mainPage.apiCommunicationError'));
    }).finally(() => {
      setLoading(false);
    });
  }, [cityName, pageNumber, editComplete]);

  const onSearchChange = (value) => {
    setCityName(value);
    setPageNumber(1);
  };

  const handlePaginationChange = (activePage) => setPageNumber(activePage);

  const handleEditComplete = (value) => {
    setEditComplete(value);
  };

  return (
    <Container>
      {error && <Message negative>{error}</Message>}
      <Segment loading={loading} color="blue">
        <Grid stackable divided>
          <Grid.Row columns="2">
            <Grid.Column width="3">
              <Header as="h2">
                <Icon name="address book" />
                <Header.Content>{t('mainPage.header')}</Header.Content>
              </Header>
            </Grid.Column>
            <Grid.Column>
              <CitySearchPanel onChange={onSearchChange} />
            </Grid.Column>
          </Grid.Row>
        </Grid>
        <CityList onEditComplete={handleEditComplete} cities={cities} />
        {cities
            && cities.length > 0
            && (
            <PaginationPanel
              pageNumber={pageNumber}
              onPageChange={handlePaginationChange}
              totalPages={totalPages}
            />
            )}
      </Segment>
    </Container>
  );
}

export default MainPage;
