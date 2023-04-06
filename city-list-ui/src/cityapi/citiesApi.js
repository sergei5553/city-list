import axios from 'axios';
import { getNumberOfCitiesUrl, getCitiesUrl, getUpdateCityUrl } from './commonUrlResolver';
import { getAuthHeader } from './userContextUtils';

export const getNumberOfCities = () => axios.get(
  getNumberOfCitiesUrl(),
  { headers: getAuthHeader() },
);

export const getCities = (name, pageNumber) => axios.get(
  getCitiesUrl(name, pageNumber),
  { headers: getAuthHeader() },
);

export const updateCity = (data) => axios.put(
  getUpdateCityUrl(data.id),
  data,
  { headers: getAuthHeader() },
);
