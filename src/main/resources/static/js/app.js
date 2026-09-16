/**
 * DevGuardian Main Application Frontend Controller
 * Handles dynamic data population, API synchronization, and state management.
 */

document.addEventListener("DOMContentLoaded", () => {
  // 1. Guard check: Redirect to login if token is missing
  const token = localStorage.getItem('jwt_token');
  if (!token && window.location.pathname !== '/login.html') {
    window.location.href = '/login.html';
    return;
  }

  // 2. Initialize dashboard data on load
  loadRepositories();
});

/**
 * Fetch connected repositories from backend API and populate the dashboard table
 */
async function loadRepositories() {
  try {
    const response = await apiCall('/api/repos');
    if (!response || !response.ok) return;

    const repos = await response.json();
    const tbody = document.getElementById('repo-table-body');

    if (repos && repos.length > 0) {
      tbody.innerHTML = repos.map(repo => `
        <tr>
          <td class="name">${repo.name || repo.url}</td>
          <td><span class="badge badge-safe">${repo.status || 'Active'}</span></td>
          <td>${repo.healthScore || '95.0'}</td>
          <td class="desc">Just now</td>
          <td><button class="btn btn-outline" style="padding: 4px 10px; font-size: 11px;" onclick="viewRepoDetails('${repo.id}')">View Details</button></td>
        </tr>
      `).join('');
    }
  } catch (err) {
    console.error('Failed to load repositories from backend:', err);
  }
}

/**
 * Trigger an active security scan across linked repositories
 */
async function triggerScan() {
  try {
    const response = await apiCall('/api/repos/scan', { method: 'POST' });
    if (response && response.ok) {
      alert('Security scan successfully initiated via backend scanner engine!');
      loadRepositories();
    } else {
      alert('Scan trigger sent. Check server logs for execution status.');
    }
  } catch (err) {
    alert('Error connecting to scan API endpoint.');
  }
}

/**
 * Send a prompt to the AI Assistant endpoint (Gemini integration)
 */
async function sendQuery() {
  const input = document.getElementById('chat-input');
  const chatOutput = document.getElementById('chat-output');
  const queryText = input.value.trim();

  if (!queryText) return;

  chatOutput.innerHTML += `<br><br><span style="color: var(--text);">[User]:</span> ${queryText}`;
  chatOutput.innerHTML += `<br><span style="color: var(--accent);">[DevGuardian AI]:</span> Analyzing context and generating response...`;
  input.value = '';
  chatOutput.scrollTop = chatOutput.scrollHeight;

  try {
    const response = await apiCall('/api/ai/chat', {
      method: 'POST',
      body: JSON.stringify({ prompt: queryText })
    });

    if (response && response.ok) {
      const data = await response.json();
      // Replace the loading message with actual backend response
      chatOutput.innerHTML = chatOutput.innerHTML.replace(
        'Analyzing context and generating response...',
        data.response || data.message || 'Analysis completed successfully.'
      );
    } else {
      chatOutput.innerHTML = chatOutput.innerHTML.replace(
        'Analyzing context and generating response...',
        'Error retrieving AI response from backend endpoint.'
      );
    }
  } catch (err) {
    chatOutput.innerHTML = chatOutput.innerHTML.replace(
      'Analyzing context and generating response...',
      'Network error connecting to AI service.'
    );
  }
  chatOutput.scrollTop = chatOutput.scrollHeight;
}

function viewRepoDetails(repoId) {
  alert(`Opening security configuration panel for Repository ID: ${repoId}`);
}