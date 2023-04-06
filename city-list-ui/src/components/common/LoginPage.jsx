import React, { useState } from 'react';
import { Navigate, useNavigate } from 'react-router-dom';
import {
  Button, Grid, Segment, Message, Form,
} from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import { saveAuthToken, saveUserContext, removeUserContext } from '../../cityapi/userContextUtils';
import { doLogin } from '../../cityapi/loginApi';
import classes from './LoginPage.module.css';

function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const { t } = useTranslation('common');

  const onLoginClick = () => {
    if (!username || !password) {
      setError(t('loginPage.incorrectInputError'));
    } else {
      saveAuthToken(username, password);
      doLogin().then((response) => {
        const { name } = response.data;
        setIsLoggedIn(!!name);
        setError(null);
        saveUserContext(response.data);
        navigate('/');
        window.location.reload();
      }).catch(() => {
        setError(t('loginPage.authenticationError'));
        removeUserContext();
      });
    }
  };

  const handleUserNameChange = (e, { value }) => {
    setUsername(value);
  };

  const handlePasswordChange = (e, { value }) => {
    setPassword(value);
  };

  if (isLoggedIn) {
    return (<Navigate to="/" />);
  }
  return (
    <Grid textAlign="center">
      <Grid.Column className={classes.column}>
        <Segment>
          <Form>
            <Form.Input
              fluid
              autoFocus
              name="username"
              icon="user"
              iconPosition="left"
              placeholder={t('loginPage.usernamePlaceholder')}
              onChange={handleUserNameChange}
            />
            <Form.Input
              fluid
              name="password"
              icon="lock"
              iconPosition="left"
              placeholder={t('loginPage.passwordPlaceholder')}
              type="password"
              onChange={handlePasswordChange}
            />
            <Button color="orange" fluid size="large" onClick={onLoginClick}>{t('loginPage.submitButton')}</Button>
          </Form>
        </Segment>
        {error && <Message negative>{error}</Message>}
      </Grid.Column>
    </Grid>
  );
}

export default LoginPage;
