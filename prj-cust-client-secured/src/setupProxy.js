const proxy = require('http-proxy-middleware');

module.exports = function(app) {

    app.use(proxy('/api', { target: 'http://localhost:8080/' }));
    app.use(proxy('/account', { target: 'http://localhost:8081/' }));

/*     app.use(proxy('/api', { target: 'http://10.111.48.90:8080/' }));
    app.use(proxy('/account', { target: 'http://10.104.111.92:8081/' })); */

};