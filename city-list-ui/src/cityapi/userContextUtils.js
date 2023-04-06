const basicAuth = (token) => `Basic ${token}`;

const getAuthToken = () => sessionStorage.getItem('AUTH_TOKEN');

export const getAuthHeader = () => ({ Authorization: basicAuth(getAuthToken()) });

export const saveAuthToken = (userName, password) => {
  sessionStorage.setItem('AUTH_TOKEN', window.btoa(`${userName}:${password}`));
};

export const saveUserContext = (context) => {
  sessionStorage.setItem('USER_NAME', context.name);
  sessionStorage.setItem('USER_AUTHORITIES', context.authorities);
};

export const isAuthenticated = () => (!!getAuthToken());

export const isNotAuthenticated = () => (!getAuthToken());

export const getUserContext = () => ({ name: sessionStorage.getItem('USER_NAME'), authorities: sessionStorage.getItem('USER_AUTHORITIES') });

export const removeUserContext = () => {
  sessionStorage.removeItem('USER_NAME');
  sessionStorage.removeItem('USER_AUTHORITIES');
  sessionStorage.removeItem('AUTH_TOKEN');
};

export const isFullAccessUser = () => {
  if (sessionStorage.getItem('USER_AUTHORITIES')) {
    return sessionStorage.getItem('USER_AUTHORITIES').includes('FULL_ACCESS');
  }
  return false;
};
