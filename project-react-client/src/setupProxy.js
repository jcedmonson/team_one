const proxy = require('http-proxy-middleware');

function determineProxy(){

    let auth_ip = process.env['AUTH_IP_ADDRESS'];
    let data_ip = process.env['DATA_IP_ADDRESS'];
    if (auth_ip && data_ip) {
        return [auth_ip, data_ip];
    } else {
        return ['http://localhost:8080/', 'http://localhost:8081/'];
    }
}



module.exports = function(app) {

    let ips = determineProxy()
    console.log(ips);


    app.use(proxy('/api', { target: ips[0]}));
    app.use(proxy('/account', { target: ips[1]}));

/*     app.use(proxy('/api', { target: 'http://10.111.48.90:8080/' }));
    app.use(proxy('/account', { target: 'http://10.104.111.92:8081/' })); */

};