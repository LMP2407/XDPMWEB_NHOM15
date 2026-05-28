export const Order = {
  "name": "Order",
  "type": "object",
  "properties": {
    "order_code": {
      "type": "string",
      "title": "M\u00e3 \u0111\u01a1n h\u00e0ng"
    },
    "customer_name": {
      "type": "string",
      "title": "T\u00ean kh\u00e1ch h\u00e0ng"
    },
    "customer_phone": {
      "type": "string",
      "title": "S\u1ed1 \u0111i\u1ec7n tho\u1ea1i"
    },
    "customer_email": {
      "type": "string",
      "title": "Email"
    },
    "items": {
      "type": "array",
      "title": "S\u1ea3n ph\u1ea9m",
      "items": {
        "type": "object",
        "properties": {
          "product_id": {
            "type": "string"
          },
          "product_name": {
            "type": "string"
          },
          "quantity": {
            "type": "number"
          },
          "price": {
            "type": "number"
          }
        }
      }
    },
    "total": {
      "type": "number",
      "title": "T\u1ed5ng ti\u1ec1n (VN\u0110)"
    },
    "status": {
      "type": "string",
      "title": "Tr\u1ea1ng th\u00e1i",
      "enum": [
        "pending",
        "confirmed",
        "shipping",
        "delivered",
        "cancelled"
      ],
      "default": "pending"
    },
    "payment_method": {
      "type": "string",
      "title": "Ph\u01b0\u01a1ng th\u1ee9c thanh to\u00e1n",
      "enum": [
        "cash",
        "transfer",
        "card",
        "momo",
        "zalopay"
      ]
    },
    "shipping_address": {
      "type": "string",
      "title": "\u0110\u1ecba ch\u1ec9 giao h\u00e0ng"
    },
    "notes": {
      "type": "string",
      "title": "Ghi ch\u00fa"
    }
  },
  "required": [
    "order_code",
    "customer_name",
    "customer_phone",
    "total",
    "status"
  ]
};
