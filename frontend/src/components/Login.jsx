import { useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    
    // Create form data format expected by Spring Security
    const params = new URLSearchParams();
    params.append('username', username);
    params.append('password', password);

    try {
      // We don't need 'files/...' prefix because of the proxy in vite.config.js
      await axios.post('/login', params, {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
      });
      
      // If no error thrown, login success
      localStorage.setItem("isLoggedIn", "true");
      navigate('/dashboard');
    } catch (err) {
      setError('Invalid credentials');
    }
  };

  return (
    <div className="card">
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
        <input 
          type="text" placeholder="Username" 
          value={username} onChange={e => setUsername(e.target.value)} required 
        />
        <input 
          type="password" placeholder="Password" 
          value={password} onChange={e => setPassword(e.target.value)} required 
        />
        <button type="submit">Login</button>
      </form>
      {error && <p className="error">{error}</p>}
      <p>New here? <Link to="/register">Register</Link></p>
    </div>
  );
};

export default Login;