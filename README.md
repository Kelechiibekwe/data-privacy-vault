# Data Privacy Vault

The **Data Privacy Vault** is a Node.js-based application that handles the tokenization and encryption of sensitive data, allowing for secure storage and retrieval. This project uses AES encryption along with format-preserving encryption (FPE) to handle sensitive fields such as credit card numbers while preserving their format.

## Features

- **Tokenization**: Generates tokens for sensitive data.
- **Encryption**: Uses AES-256-CBC for encrypting data.
- **Format-Preserving Encryption (FPE)**: Encrypts numeric fields like credit card numbers while maintaining their format.
- **Redis Integration**: Stores encrypted data in Redis for fast and secure retrieval.
- **De-tokenization**: Retrieves and decrypts tokenized data.

## Technologies Used

- **Node.js**: JavaScript runtime used for building the API.
- **Express**: Web framework for creating the API routes.
- **Redis**: In-memory key-value store used for storing tokens and encrypted data.
- **AES-256-CBC**: Encryption standard used for securely encrypting the data.
- **Format-Preserving Encryption (FPE)**: Ensures sensitive numeric fields maintain their format during encryption.
- **node-fpe**: Library for applying format-preserving encryption.
