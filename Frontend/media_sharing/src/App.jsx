import logo from './logo.png';
import './App.css';
import Login from './pages/login'
import Upload from './pages/upload'
import Register from './pages/register'
import Home from './pages/Home';
import{BrowserRouter as Router,Routes,Route, HashRouter } from 'react-router-dom'
import UserContex from '../src/components/Context'
import { useState } from 'react';

function App() {
  const [user, setUser] = useState({});
  const value = {user, setUser}
  return (

      <UserContex.Provider value={value}>
        <HashRouter>
          <Routes>
              <Route path='/' element={<Login></Login>}></Route>
              <Route path='/login' element={<Login></Login>}></Route>
              <Route path='/register' element={<Register></Register>}></Route>
              <Route path='/uploads' element={<Upload></Upload>}></Route>
              <Route path='/home' element={<Home></Home>}></Route>
          </Routes>
       </HashRouter>
      </UserContex.Provider>
  
    
  );
}

export default App;
