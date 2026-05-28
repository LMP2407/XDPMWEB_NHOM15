export const Product = {
  "name": "Product",
  "type": "object",
  "properties": {
    "name": {
      "type": "string",
      "title": "T\u00ean s\u1ea3n ph\u1ea9m"
    },
    "brand": {
      "type": "string",
      "title": "Th\u01b0\u01a1ng hi\u1ec7u",
      "enum": [
        "Apple",
        "Samsung",
        "Xiaomi",
        "OPPO",
        "Vivo",
        "Realme",
        "OnePlus",
        "Google",
        "Huawei",
        "Kh\u00e1c"
      ]
    },
    "category_id": {
      "type": "string",
      "title": "Danh m\u1ee5c"
    },
    "price": {
      "type": "number",
      "title": "Gi\u00e1 b\u00e1n (VN\u0110)"
    },
    "cost_price": {
      "type": "number",
      "title": "Gi\u00e1 nh\u1eadp (VN\u0110)"
    },
    "stock": {
      "type": "number",
      "title": "T\u1ed3n kho"
    },
    "image_url": {
      "type": "string",
      "title": "\u1ea2nh s\u1ea3n ph\u1ea9m"
    },
    "specs": {
      "type": "string",
      "title": "Th\u00f4ng s\u1ed1 k\u1ef9 thu\u1eadt"
    },
    "status": {
      "type": "string",
      "title": "Tr\u1ea1ng th\u00e1i",
      "enum": [
        "active",
        "inactive",
        "out_of_stock"
      ],
      "default": "active"
    },
    "color": {
      "type": "string",
      "title": "M\u00e0u s\u1eafc"
    },
    "storage": {
      "type": "string",
      "title": "Dung l\u01b0\u1ee3ng",
      "enum": [
        "32GB",
        "64GB",
        "128GB",
        "256GB",
        "512GB",
        "1TB"
      ]
    }
  },
  "required": [
    "name",
    "brand",
    "price",
    "stock",
    "status"
  ]
};