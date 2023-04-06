import axios from 'axios';
import { getLoginUrl } from './commonUrlResolver';

import { getAuthHeader } from './userContextUtils';

export const doLogin = () => axios.get(getLoginUrl(), {
  headers: getAuthHeader(),
});
