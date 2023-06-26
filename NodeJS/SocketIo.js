let ioSocket;

function set(io) {
    ioSocket = io;
}

function get(){
    return ioSocket;
}

module.exports = {
    set, get
}