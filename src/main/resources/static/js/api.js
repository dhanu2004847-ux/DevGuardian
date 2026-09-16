// Centralized authenticated fetch wrapper
async function apiCall(url, options = {}) {
  const token = localStorage.getItem('jwt_token');

  const headers = {
    'Content-Type': 'application/json',
    ...(token ? { 'Authorization': `Bearer ${token}` } : {}),
    ...(options.headers || {})
  };

  const response = await fetch(url, { ...options, headers });

  if (response.status === 401) {
    // Unauthorized: redirect to login
    localStorage.removeItem('jwt_token');
    window.location.href = '/login.html';
    return;
  }

  return response;
}