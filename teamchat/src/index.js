import React from 'react';
import ReactDOM from 'react-dom/client';
import Registration from './Registration/Registration';
import Login from './Login/Login';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { useState } from 'react';
import Chat from './Chat/Chat';

const root = ReactDOM.createRoot(document.getElementById('root'));

const App = () => {
  // const [list, setList] = useState([]);
  const [user, setUser] = useState(null);

  return (
    <React.StrictMode>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Login setUser={setUser}/>} exact />
          <Route path="/registration" element={<Registration />} />
          <Route path="/Chat" element={user ? <Chat user={user}/> : <Navigate replace to='/' />} />
        </Routes>
      </BrowserRouter>
    </React.StrictMode>
  );
};

root.render(<App />);
