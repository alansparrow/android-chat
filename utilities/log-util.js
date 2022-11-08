const envMonitor = require('./env-monitor');

/**
 * Log if env is dev
 * @param {string} tag tag
 * @param {string} msg log msg
 */
function logDev(tag, msg) {
  if (envMonitor.isDev() == true) {
    console.log('[' + new Date() + '][dev] [' + tag + '] ' + msg);
  }
}

/**
 * Log
 * @param {string} tag tag
 * @param {string} msg log msg
 */
function log(tag, msg) {
  console.log('[' + new Date() + '][' + tag + '] ' + msg);
}

/**
 * Log error
 * @param {string} tag tag
 * @param {string} msg log msg
 * @param {error} err error obj
 */
function logError(tag, msg, err) {
  console.error('[' + new Date() + '][' + tag + '] ' + msg, err);
}

const separators = {
  LINE_5: '=====',
};

module.exports = {
  logDev,
  log,
  logError,
  separators,
};
