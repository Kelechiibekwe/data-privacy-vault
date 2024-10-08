const express = require('express');
const app = express();
const tokenController = require('./controllers/tokenController');

// Middleware
app.use(express.json());

// Routes
app.post('/tokenize', tokenController.tokenize);
app.post('/detokenize', tokenController.detokenize);
app.get('/', tokenController.helloWorld);

// Start server
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});