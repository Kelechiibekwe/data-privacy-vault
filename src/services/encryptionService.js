const crypto = require('crypto');
const fpe = require('node-fpe');

// Encryption key (should be stored securely)
const ENCRYPTION_KEY = crypto.randomBytes(32); // 256-bit key
const IV_LENGTH = 16; // AES block size

// AES Encrypt function
function encrypt(text) {
    const iv = crypto.randomBytes(IV_LENGTH);
    const cipher = crypto.createCipheriv('aes-256-cbc', ENCRYPTION_KEY, iv);
    let encrypted = cipher.update(text, 'utf8', 'hex');
    encrypted += cipher.final('hex');
    return iv.toString('hex') + ':' + encrypted;
}

// AES Decrypt function
function decrypt(text) {
    const [ivHex, encryptedText] = text.split(':');
    const iv = Buffer.from(ivHex, 'hex');
    const decipher = crypto.createDecipheriv('aes-256-cbc', ENCRYPTION_KEY, iv);
    let decrypted = decipher.update(encryptedText, 'hex', 'utf8');
    decrypted += decipher.final('utf8');
    return decrypted;
}

// Format-preserving encryption
function formatPreservingEncrypt(value) {
    const key = ENCRYPTION_KEY.toString('hex');
    return value.replace(/\d+/g, (match) => fpe.encrypt(key, '0123456789', match));
}

function formatPreservingDecrypt(value) {
    const key = ENCRYPTION_KEY.toString('hex');
    return value.replace(/\d+/g, (match) => fpe.decrypt(key, '0123456789', match));
}

// Generate a unique token
function generateToken() {
    return crypto.randomBytes(16).toString('hex');
}

function encryptValue(value) {
    if (typeof value === 'string' && /\d/.test(value)) {
        return formatPreservingEncrypt(value);
    } else {
        return encrypt(value);
    }
}

function decryptValue(value) {
    if (typeof value === 'string' && /\d/.test(value)) {
        return formatPreservingDecrypt(value);
    } else {
        return decrypt(value);
    }
}

module.exports = {
    encryptValue,
    decryptValue,
    generateToken
};