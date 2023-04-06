import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Container, Menu } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import {
  getUserContext, isAuthenticated, isNotAuthenticated, removeUserContext,
} from '../../cityapi/userContextUtils';
import CityStatistics from '../citiy/CityStatistics';

function HeaderPanel() {
  const { t } = useTranslation('common');

  const navigate = useNavigate();

  const logout = () => {
    removeUserContext();
    navigate('/');
    window.location.reload(false);
  };

  return (
    <Menu inverted color="black" stackable size="large">
      <Container>
        <Menu.Item>{t('header.title')}</Menu.Item>
        <CityStatistics />
        <Menu.Menu position="right">
          {isNotAuthenticated() && <Menu.Item as={Link} to="/login">{t('header.link.login')}</Menu.Item>}
          {isAuthenticated() && <Menu.Item header>{`${t('header.username')} ${getUserContext().name}`}</Menu.Item>}
          {isAuthenticated() && <Menu.Item as={Link} to="/" onClick={logout}>{t('header.link.logout')}</Menu.Item>}
        </Menu.Menu>
      </Container>
    </Menu>
  );
}

export default HeaderPanel;
