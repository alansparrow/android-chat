require('dotenv').config();
/**
 * Check if the env is dev.
 * @return {bool} true if the env is dev.
 */
function isDev() {
  return process.env.NODE_ENV.trim() === 'development';
}

/**
 * Check if the env is prod.
 * @return {bool} true if the env is prod.
 */
function isProd() {
  return process.env.NODE_ENV.trim() === 'production';
}

/**
 * Check if the env is test.
 * @return {bool} true if the env is test.
 */
function isTest() {
  return process.env.NODE_ENV.trim() === 'test';
}

module.exports = {
  isDev,
  isProd,
  isTest,
};
