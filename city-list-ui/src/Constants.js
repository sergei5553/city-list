const prod = {
  url: {
    CITY_API_BASE_URL: 'http://localhost:8080',
  },
  contextName: {
    CITY_API_CONTEXT_NAME: 'api',
  },
  version: {
    CITY_API_VERSION: '1',
  },
  api: {
    PAGE_SIZE: 10,
  },
  image: {
    NOT_EXIST: 'https://react.semantic-ui.com/images/wireframe/image.png',
  },
};

const dev = {
  url: {
    CITY_API_BASE_URL: 'http://localhost:8080',
  },
  contextName: {
    CITY_API_CONTEXT_NAME: 'api',
  },
  version: {
    CITY_API_VERSION: '1',
  },
  api: {
    PAGE_SIZE: 10,
  },
  image: {
    NOT_EXIST: 'https://react.semantic-ui.com/images/wireframe/image.png',
  },
};

export const config = process.env.NODE_ENV === 'development' ? dev : prod;
