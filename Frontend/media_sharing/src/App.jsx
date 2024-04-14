import logo from './logo.png';
import './App.css';
import Login from './components/login'
import Upload from './components/upload'
import Register from './components/register'
import Home from './components/Home';
import{BrowserRouter as Router,Routes,Route } from 'react-router-dom'
import UserContex from '../src/components/Context'
import { useState } from 'react';

function App() {
  const [userContext, setUserContext] = useState({
    avatar: '',
    id: 0,
    name:'',
});
  return (

      <UserContex.Provider value={userContext}>
        <Router>
          <Routes>
              <Route path='/' element={<Login></Login>}></Route>
              <Route path='/login' element={<Login></Login>}></Route>
              <Route path='/register' element={<Register></Register>}></Route>
              <Route path='/upload' element={<Upload></Upload>}></Route>
              <Route path='/home' element={<Home></Home>}></Route>
          </Routes>
       </Router>
      </UserContex.Provider>
  
    
  );
}

export default App;
