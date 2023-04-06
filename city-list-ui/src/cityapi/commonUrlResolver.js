import { config } from '../Constants';

function context(version) {
  const safeVersion = version || config.version.CITY_API_VERSION;
  return `${config.contextName.CITY_API_CONTEXT_NAME}/v${safeVersion}`;
}

export const getLoginUrl = () => `/${context()}/user/context`;

export const getNumberOfCitiesUrl = () => `/${context()}/cities/count`;

export const getCitiesUrl = (name, pageNumber) => {
  const searchParams = new URLSearchParams();
  if (name) {
    searchParams.append('name', name);
  }

  if (pageNumber) {
    searchParams.append('pageNumber', pageNumber);
  }

  searchParams.append('pageSize', config.api.PAGE_SIZE);

  const url = new URL(`/${context()}/cities`, window.location.origin);
  url.search = searchParams.toString();

  return url.href;
};

export const getUpdateCityUrl = (id) => `/${context()}/cities/${id}`;
