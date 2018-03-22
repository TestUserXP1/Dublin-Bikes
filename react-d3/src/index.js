import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';

ReactDOM.render(<App elementWidth={600} elementHeight={270} />, document.getElementById('root'));
registerServiceWorker();
