import logo from './logo.svg';
import './App.css';
import Login from './components/login'
import Register from './components/register'
import{BrowserRouter as Router,Routes,Route } from 'react-router-dom'

function App() {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<Login></Login>}></Route>
        <Route path='/register' element={<Register></Register>}></Route>
      </Routes>
    </Router>
  );
}

export default App;
