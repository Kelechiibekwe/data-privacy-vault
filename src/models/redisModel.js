const redis = require('redis');
const { promisify } = require('util');

// Configure Redis client
const client = redis.createClient();
client.on('error', (err) => {
    console.error('Redis error:', err);
});

// Promisify Redis methods
const setAsync = promisify(client.set).bind(client);
const getAsync = promisify(client.get).bind(client);

module.exports = {
    setAsync,
    getAsync
};