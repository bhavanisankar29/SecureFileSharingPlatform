import { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Dashboard = () => {
  const [files, setFiles] = useState([]);
  const [uploadStatus, setUploadStatus] = useState('');
  const navigate = useNavigate();

  // Load files on mount
  useEffect(() => {
    fetchFiles();
  }, []);

  const fetchFiles = async () => {
    try {
      const response = await axios.get('/files/list');
      setFiles(response.data);
    } catch (error) {
      console.error("Error fetching files", error);
      if(error.response && error.response.status === 401 || error.response.status === 403) {
        handleLogout(); // Redirect if session expired
      }
    }
  };

  const handleUpload = async (e) => {
    const file = e.target.files[0];
    if (!file) return;

    const formData = new FormData();
    formData.append("file", file);
    setUploadStatus("Uploading...");

    try {
      await axios.post('/files/upload', formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
      });
      setUploadStatus("Upload successful!");
      fetchFiles(); // Refresh list
    } catch (error) {
      setUploadStatus("Upload failed.");
    }
  };

  const handleDelete = async (id) => {
    if(!confirm("Delete this file?")) return;
    try {
      await axios.delete(`/files/delete/${id}`);
      fetchFiles();
    } catch (error) {
      alert("Error deleting file");
    }
  };

  const handleDownload = (id, filename) => {
    // We can't use Axios for download easily because we need the browser to handle the file stream.
    // We use standard window.location, utilizing the session cookie automatically.
    window.location.href = `/files/download/${id}`;
  };

  const handleLogout = async () => {
    try {
      await axios.post('/logout');
    } catch(e) { /* ignore */ }
    localStorage.removeItem("isLoggedIn");
    navigate('/login');
  };

  return (
    <div className="container">
      <div className="header">
        <h1>My Files</h1>
        <button onClick={handleLogout} className="logout-btn">Logout</button>
      </div>

      <div className="upload-section">
        <label className="custom-file-upload">
          <input type="file" onChange={handleUpload} />
          Select File to Upload
        </label>
        <span className="status">{uploadStatus}</span>
      </div>

      <table className="file-table">
        <thead>
          <tr>
            <th>Filename</th>
            <th>Size (Bytes)</th>
            <th>Date</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {files.map(file => (
            <tr key={file.id}>
              <td>{file.filename}</td>
              <td>{file.size}</td>
              <td>{new Date(file.uploadTime).toLocaleDateString()}</td>
              <td>
                <button 
                  className="btn download" 
                  onClick={() => handleDownload(file.id, file.filename)}>
                  Download
                </button>
                <button 
                  className="btn delete" 
                  onClick={() => handleDelete(file.id)}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Dashboard;