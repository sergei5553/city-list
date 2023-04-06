import React from 'react';
import {
  BrowserRouter, Routes, Route, Navigate,
} from 'react-router-dom';
import HeaderPanel from './components/common/HeaderPanel';
import LoginPage from './components/common/LoginPage';
import MainPage from './components/common/MainPage';
import { isAuthenticated } from './cityapi/userContextUtils';

function App() {
  return (
    <BrowserRouter>
      <HeaderPanel />
      <Routes>
        <Route path="/" element={isAuthenticated() ? <MainPage /> : <LoginPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
