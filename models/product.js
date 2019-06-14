var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var productSchema = new Schema({
    name: { type: String, unique: true, required: true, dropDups: true },
    detall: { type: String, default: null },
    image: { type: String, default: null },
    cate_id: { type: String, default: null },
    price: { type: String, default: null }
});


module.exports = mongoose.model('products', productSchema);