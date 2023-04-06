import {
  getAuthHeader,
  saveAuthToken,
  saveUserContext,
  isAuthenticated,
  isNotAuthenticated,
  getUserContext,
} from '../../cityapi/userContextUtils';

describe('userContextUtils', () => {
  describe('getAuthHeader', () => {
    it('returns an object with the Authorization header containing a basic auth token', () => {
      sessionStorage.setItem('AUTH_TOKEN', 'test-token');
      const expected = { Authorization: 'Basic test-token' };
      const result = getAuthHeader();
      expect(result).toEqual(expected);
    });
  });

  describe('saveAuthToken', () => {
    it('saves a base64-encoded string of the username and password to sessionStorage as the AUTH_TOKEN key', () => {
      const username = 'test-username';
      const password = 'test-password';
      saveAuthToken(username, password);
      const expected = window.btoa(`${username}:${password}`);
      const result = sessionStorage.getItem('AUTH_TOKEN');
      expect(result).toEqual(expected);
    });
  });

  describe('saveUserContext', () => {
    it('saves the user name and authorities to sessionStorage', () => {
      const context = { name: 'test-name', authorities: 'test-authorities' };
      saveUserContext(context);
      const expectedName = 'test-name';
      const expectedAuthorities = 'test-authorities';
      const resultName = sessionStorage.getItem('USER_NAME');
      const resultAuthorities = sessionStorage.getItem('USER_AUTHORITIES');
      expect(resultName).toEqual(expectedName);
      expect(resultAuthorities).toEqual(expectedAuthorities);
    });
  });

  describe('isAuthenticated', () => {
    it('returns true if there is an AUTH_TOKEN in sessionStorage', () => {
      sessionStorage.setItem('AUTH_TOKEN', 'test-token');
      const expected = true;
      const result = isAuthenticated();
      expect(result).toEqual(expected);
    });

    it('returns false if there is no AUTH_TOKEN in sessionStorage', () => {
      sessionStorage.removeItem('AUTH_TOKEN');
      const expected = false;
      const result = isAuthenticated();
      expect(result).toEqual(expected);
    });
  });

  describe('isNotAuthenticated', () => {
    it('returns true if there is no AUTH_TOKEN in sessionStorage', () => {
      sessionStorage.removeItem('AUTH_TOKEN');
      const expected = true;
      const result = isNotAuthenticated();
      expect(result).toEqual(expected);
    });

    it('returns false if there is an AUTH_TOKEN in sessionStorage', () => {
      sessionStorage.setItem('AUTH_TOKEN', 'test-token');
      const expected = false;
      const result = isNotAuthenticated();
      expect(result).toEqual(expected);
    });
  });

  describe('getUserContext', () => {
    it('returns an object with the user name and authorities from sessionStorage', () => {
      sessionStorage.setItem('USER_NAME', 'test-name');
      sessionStorage.setItem('USER_AUTHORITIES', 'test-authorities');
      const expected = { name: 'test-name', authorities: 'test-authorities' };
      const result = getUserContext();
      expect(result).toEqual(expected);
    });
  });
});
