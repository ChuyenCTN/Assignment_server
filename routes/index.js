var express = require('express');
var router = express.Router();
var Category = require('../models/category');
var Product = require('../models/product');

var shortid = require('shortid');
var multer = require('multer');
//config noi luu tru va ten anh upload len
var storage = multer.diskStorage({
  // noi luu tru 
  destination: function (req, file, cb) {
    cb(null, './public/image_uploads');
  },
  // cau hinh ten file 
  // giu nguyen ten file goc
  filename: function (req, file, cb) {
    cb(null, shortid.generate() + "--" + file.originalname);
  }
});
var upload = multer({ storage: storage });

/* GET home page. */
// router.get('/', function (req, res, next) {
//   Product.find({})
//     .populate('cate_id')
//     .exec((err, data) => {
//       console.log(data);
//       res.render('index', { products: data });
//     });
// });
//Product
router.get('/', function (req, res, next) {
  Product.find({}, function (err, data) {
    res.render('index', { products: data });
  });
});
// router.get('/', function (req, res, next) {
//   Product.find({})
//     .populate('cate_id')
//     .exec((err, data) => {
//       console.log(data);
//       res.render('index', { products: data });
//     });
// });

router.post('/products/save-edit', upload.single('image_pr'), function (req, res, next) {
  Product.findOne({ _id: req.body.id }, function (err, model_pr) {
    if (err) {
      res.redirect('back');
    }
    model_pr.name = req.body.name_pr;
    model_pr.detall = req.body.detall_pr;
    model_pr.price = req.body.price_pr;
    model_pr.cate_id = req.body.cateid_pr;
    if (req.file != null) {
      model_pr.image = req.file.path.replace('public', '');
    }
    model_pr.save(function (err) {
      if (err) {
        res.send('Luu khong thanh cong');
      }
      res.redirect('/');
    })
  })
});

router.get('/products/remove', function (req, res, next) {
  res.render('./products/remove', { title: 'RemoveProducts' });
});
router.get('/products/add', function (req, res, next) {
  Category.find({}, function (err, data) {
    res.render('./products/add', { cates: data });
  });
});

router.post('/products/save-add', upload.single('image_pr'), function (req, res, next) {
  var model_pr = new Product();
  model_pr.name = req.body.name_pr;
  model_pr.image = req.file.path.replace('public', '');
  model_pr.detall = req.body.detall_pr;
  model_pr.cate_id = req.body.cateid_pr;
  model_pr.price = req.body.price_pr;
  model_pr.save(function (err) {
    if (err) {
      res.send("Luu khong thanh cong");
    }
    else {
      res.redirect('/');
    }
  });
});

router.get('/products/edit/:PId', function (req, res, next) {
  Category.find({}, (err, data) => {
    Product.findOne({ _id: req.params.PId }, (err, productData) => {
      if (err) {
        res.send('Id san pham khong ton tai');
      }
      for (var i = 0; i < data.length; i++) {
        if (data[i]._id == productData.cate_id.toString()) {
          data[i].selected = true;
        }
      }
      res.render('./products/edit', { cates: data, products: productData });
    });
  });
});

// Categories

router.get('/cates', function (req, res, next) {
  Category.find({}, function (err, data) {
    res.render('cates', { cates: data });
  });
});
router.get('/cates', function (req, res, next) {
  res.render('cates', { title: 'Cates' });
});
router.get('/cates/add', function (req, res, next) {
  res.render('./cates/add');
});
router.get('/cates/edit/:cId', function (req, res, next) {
  Category.findOne({ _id: req.params.cId }, function (err, data) {
    if (err) {
      res.send('Id khong ton tai');
    }
    res.render('cates/edit', { cates: data });
  })
});

router.get('/cates/remove/:cID', function (req, res, next) {
  Category.findOne({ _id: req.params.cID }, function (err, data) {
    if (err) {
      res.send('ID khong ton tai');
    }
    res.render('cates/remove', { cates: data })
  })
});
router.post('/cates/save-edit', upload.single('image'), function (req, res, next) {
  Category.findOne({ _id: req.body.id }, function (err, model) {
    if (err) {
      res.redirect('back');
    }
    model.name = req.body.name;
    model.description = req.body.description;
    if (req.file != null) {
      model.image = req.file.path.replace('public', '');
    }
    model.save(function (err) {
      if (err) {
        res.send('Luu khong thanh cong');
      }
      res.redirect('/cates');
    })
  })
});

router.get('/cates/remove-cate', function (req, res, next) {
  Category.deleteOne({ _id: req.body.id }, function (err) {
    if (err) {
      return handleError(err);
    }
    res.redirect('/cates');
  });
});

router.get('/cates/remove/:cId', function (req, res, next) {
  Category.deleteOne({ _id: req.params.cId }, function (err) {
    if (err) res.send('gdsfjgkdfgjk');
    // deleted at most one tank document
    res.redirect('/cates');
  });
});

router.post('/cates/save-add', upload.single('image'), function (req, res, next) {
  // res.json(req.file.path.replace('public',''));
  // 1. lay data tu form gui len
  //2. tao model
  var model = new Category();

  //3. gan gia tri cho model
  model.name = req.body.name;
  model.image = req.file.path.replace('public', '');
  model.description = req.body.description;

  // 4.save 
  model.save(function (err) {
    if (err) {
      res.send("Luu khong thanh cong");
    }
    else {
      res.redirect('/cates');
    }
  });
});
module.exports = router;
