import { useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';

const Register = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();
    const params = new URLSearchParams();
    params.append('username', username);
    params.append('password', password);

    try {
      const baseUrl = "http://localhost:8080";
      await axios.post('/register', params);
      alert("Registration successful! Please login.");
      navigate('/login');
    } catch (err) {
      alert("Registration failed. User might exist.");
    }
  };

  return (
    <div className="card">
      <h2>Register</h2>
      <form onSubmit={handleRegister}>
        <input 
          type="text" placeholder="Choose Username" 
          value={username} onChange={e => setUsername(e.target.value)} required 
        />
        <input 
          type="password" placeholder="Choose Password" 
          value={password} onChange={e => setPassword(e.target.value)} required 
        />
        <button type="submit">Register</button>
      </form>
      <p>Already have an account? <Link to="/login">Login</Link></p>
    </div>
  );
};

export default Register;