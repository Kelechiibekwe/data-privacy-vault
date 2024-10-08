const redisModel = require('../models/redisModel.js');
const encryptionService = require('../services/encryptionService');

// Tokenize function
exports.tokenize = async (req, res) => {
    try {
        const { id, data } = req.body;
        if (!id || !data || typeof data !== 'object') {
            return res.status(400).json({ error: 'Invalid request payload' });
        }

        let tokenizedData = {};
        for (let field in data) {
            const value = data[field];
            const token = encryptionService.generateToken();
            const encryptedValue = encryptionService.encryptValue(value);
            await redisModel.setAsync(token, encryptedValue);
            tokenizedData[field] = token;
        }

        res.status(201).json({ id, data: tokenizedData });
    } catch (error) {
        console.error('Tokenization error:', error);
        res.status(500).json({ error: 'Internal server error' });
    }
};

// Detokenize function
exports.detokenize = async (req, res) => {
    try {
        const { id, data } = req.body;
        if (!id || !data || typeof data !== 'object') {
            return res.status(400).json({ error: 'Invalid request payload' });
        }

        let detokenizedData = {};
        for (let field in data) {
            const token = data[field];
            const encryptedValue = await redisModel.getAsync(token);
            if (encryptedValue) {
                const value = encryptionService.decryptValue(encryptedValue);
                detokenizedData[field] = { found: true, value };
            } else {
                detokenizedData[field] = { found: false, value: '' };
            }
        }

        res.status(200).json({ id, data: detokenizedData });
    } catch (error) {
        console.error('Detokenization error:', error);
        res.status(500).json({ error: 'Internal server error' });
    }
};

// Hello World
exports.helloWorld = async (req, res) => {
        res.send('Hello world');
};