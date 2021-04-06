var total=0;
$(document).ready(function() {
    $("#cup").on("click", function () {
        event.preventDefault();
        var price = 80;
        var itemName = "Cup";

        $.ajax({
            url: "/cartItem",
            method: "post",
            data: {"price":price,"item":itemName},

            success: function (data) {
                if(data==1)
                {
                    total=total+price;
                    var row = '<div class="item"><label class="item">'+itemName+'</label>\n' +
                        '            <span class="price">'+price+'</span>\n' +
                        '            <span ><button class="remove"><span class="glyphicon glyphicon-remove"></span></button></span><br></div>';
                    $("#cart").append(row);
                    $("#cartTotal").html(total);
                }
                else
                {
                    alert("Cannot add product to cart.Points insufficient!! ")
                }
            }
        });
    });
    $('#cart').on('click', '.remove', function(e) {
        $(this).parents('.item').remove();
        price=80;
        itemName="cup";
        $.ajax({
            url: "/deleteItem",
            method: "post",
            data: {"price":price,"item":itemName},

            success: function (data) {
                if(data==1)
                {
                    total=total-price;
                    $("#cartTotal").html(total);
                }
            }
        });
    });












    $("#headphone").on("click", function () {
        event.preventDefault();
        var price = 500;
        var itemName = "Headphone";
        $.ajax({
            url: "/cartItem",
            method: "post",
            data: {"price":price,"item":itemName},

            success: function (data) {
                if(data==1)
                {
                    total=total+price;
                    var row = '<div class="item"><label class="item">'+itemName+'</label>\n' +
                        '            <span class="price">'+price+'</span>\n' +
                        '            <span ><button class="removeHeadphone"><span class="glyphicon glyphicon-remove"></span></button></span><br></div>';
                    $("#cart").append(row);

                    $("#cartTotal").html(total);
                }
                else
                {
                    alert("Cannot add product to cart.Points insufficient!! ")
                }
            }
        });
    });
    $('#cart').on('click', '.removeHeadphone', function(e) {
        $(this).parents('.item').remove();
        price=500;
        itemName="Headphone";
        $.ajax({
            url: "/deleteItem",
            method: "post",
            data: {"price":price,"item":itemName},

            success: function (data) {
                if(data==1)
                {
                    total=total-price;
                    $("#cartTotal").html(total);
                }
            }
        });
    });


















    $("#pen").on("click", function () {
        event.preventDefault();
        var price = 50;
        var itemName = "Pen";
        $.ajax({
            url: "/cartItem",
            method: "post",
            data: {"price":price,"item":itemName},

            success: function (data) {
                if(data==1)
                {
                    total=total+price;
                    var row = '<div class="item"><label class="item">'+itemName+'</label>\n' +
                        '            <span class="price">'+price+'</span>\n' +
                        '            <span ><button class="removePen"><span class="glyphicon glyphicon-remove"></span></button></span><br></div>';
                    $("#cart").append(row);

                    $("#cartTotal").html(total);
                }
                else
                {
                    alert("Cannot add product to cart.Points insufficient!! ")
                }
            }
        });
    });
    $('#cart').on('click', '.removePen', function(e) {
        $(this).parents('.item').remove();
        price=50;
        itemName="Pen";
        $.ajax({
            url: "/deleteItem",
            method: "post",
            data: {"price":price,"item":itemName},

            success: function (data) {
                if(data==1)
                {
                    total=total-price;
                    $("#cartTotal").html(total);
                }
            }
        });
    });



















    $("#bag").on("click", function () {
        event.preventDefault();
        var price = 300;
        var itemName = "Bag";
        $.ajax({
            url: "/cartItem",
            method: "post",
            data: {"price":price,"item":itemName},

            success: function (data) {
                if(data==1)
                {
                    total=total+price;
                    var row = '<div class="item"><label class="item">'+itemName+'</label>\n' +
                        '            <span class="price">'+price+'</span>\n' +
                        '            <span ><button class="removeBag"><span class="glyphicon glyphicon-remove"></span></button></span><br></div>';
                    $("#cart").append(row);

                    $("#cartTotal").html(total);
                }
                else
                {
                    alert("Cannot add product to cart.Points insufficient!! ")
                }
            }
        });
    });
    $('#cart').on('click', '.removeBag', function(e) {
        $(this).parents('.item').remove();
        price=300;
        itemName="Bag";
        $.ajax({
            url: "/deleteItem",
            method: "post",
            data: {"price":price,"item":itemName},

            success: function (data) {
                if(data==1)
                {
                    total=total-price;
                    $("#cartTotal").html(total);
                }
            }
        });
    });



























    $("#pendrive").on("click", function () {
        event.preventDefault();
        var price = 300;
        var itemName = "Pendrive";
        $.ajax({
            url: "/cartItem",
            method: "post",
            data: {"price":price,"item":itemName},

            success: function (data) {
                if(data==1)
                {
                    total=total+price;
                    var row = '<div class="item"><label class="item">'+itemName+'</label>\n' +
                        '            <span class="price">'+price+'</span>\n' +
                        '            <span ><button class="removePendrive"><span class="glyphicon glyphicon-remove"></span></button></span><br></div>';
                    $("#cart").append(row);

                    $("#cartTotal").html(total);
                }
                else
                {
                    alert("Cannot add product to cart.Points insufficient!! ")
                }
            }
        });
    });
    $('#cart').on('click', '.removePendrive', function(e) {
        $(this).parents('.item').remove();
        price=300;
        itemName="Pendrive";
        $.ajax({
            url: "/deleteItem",
            method: "post",
            data: {"price":price,"item":itemName},

            success: function (data) {
                if(data==1)
                {
                    total=total-price;
                    $("#cartTotal").html(total);
                }
            }
        });
    });





















    $("#folder").on("click", function () {
        event.preventDefault();
        var price = 250;
        var itemName = "Folder";
        $.ajax({
            url: "/cartItem",
            method: "post",
            data: {"price":price,"item":itemName},

            success: function (data) {
                if(data==1)
                {
                    total=total+price;
                    var row = '<div class="item"><label class="item">'+itemName+'</label>\n' +
                        '            <span class="price">'+price+'</span>\n' +
                        '            <span ><button class="removeFolder"><span class="glyphicon glyphicon-remove"></span></button></span><br></div>';
                    $("#cart").append(row);

                    $("#cartTotal").html(total);
                }
                else
                {
                    alert("Cannot add product to cart.Points insufficient!! ")
                }
            }
        });
    });
    $('#cart').on('click', '.removeFolder', function(e) {
        $(this).parents('.item').remove();
        price=250;
        itemName="Folder";
        $.ajax({
            url: "/deleteItem",
            method: "post",
            data: {"price":price,"item":itemName},

            success: function (data) {
                if(data==1)
                {
                    total=total-price;
                    $("#cartTotal").html(total);
                }
            }
        });
    });
























    $("#wallet").on("click", function () {
        event.preventDefault();
        var price = 200;
        var itemName = "Wallet";
        $.ajax({
            url: "/cartItem",
            method: "post",
            data: {"price":price,"item":itemName},

            success: function (data) {
                if(data==1)
                {
                    total=total+price;
                    var row = '<div class="item"><label class="item">'+itemName+'</label>\n' +
                        '            <span class="price">'+price+'</span>\n' +
                        '            <span ><button class="removeWallet"><span class="glyphicon glyphicon-remove"></span></button></span><br></div>';
                    $("#cart").append(row);

                    $("#cartTotal").html(total);
                }
                else
                {
                    alert("Cannot add product to cart.Points insufficient!! ")
                }
            }
        });
    });
    $('#cart').on('click', '.removeWallet', function(e) {
        $(this).parents('.item').remove();
        price=200;
        itemName="Wallet";
        $.ajax({
            url: "/deleteItem",
            method: "post",
            data: {"price":price,"item":itemName},

            success: function (data) {
                if(data==1)
                {
                    total=total-price;
                    $("#cartTotal").html(total);
                }
            }
        });
    });

















    $("#sipper").on("click", function () {
        event.preventDefault();
        var price = 350;
        var itemName = "Sipper";
        $.ajax({
            url: "/cartItem",
            method: "post",
            data: {"price":price,"item":itemName},

            success: function (data) {
                if(data==1)
                {
                    total=total+price;
                    var row = '<div class="item"><label class="item">'+itemName+'</label>\n' +
                        '            <span class="price">'+price+'</span>\n' +
                        '            <span ><button class="removeSipper"><span class="glyphicon glyphicon-remove"></span></button></span><br></div>';
                    $("#cart").append(row);

                    $("#cartTotal").html(total);
                }
                else
                {
                    alert("Cannot add product to cart.Points insufficient!! ")
                }
            }
        });
    });
    $('#cart').on('click', '.removeSipper', function(e) {
        $(this).parents('.item').remove();
        price=350;
        itemName="Sipper";
        $.ajax({
            url: "/deleteItem",
            method: "post",
            data: {"price":price,"item":itemName},

            success: function (data) {
                if(data==1)
                {
                    total=total-price;
                    $("#cartTotal").html(total);
                }
            }
        });
    });































    $("#cover").on("click", function () {
        event.preventDefault();
        var price = 150;
        var itemName = "LaptopCover";
        $.ajax({
            url: "/cartItem",
            method: "post",
            data: {"price":price,"item":itemName},

            success: function (data) {
                if(data==1)
                {
                    total=total+price;
                    var row = '<div class="item"><label class="item">'+itemName+'</label>\n' +
                        '            <span class="price">'+price+'</span>\n' +
                        '            <span ><button class="removeCover"><span class="glyphicon glyphicon-remove"></span></button></span><br></div>';
                    $("#cart").append(row);

                    $("#cartTotal").html(total);
                }
                else
                {
                    alert("Cannot add product to cart.Points insufficient!! ")
                }
            }
        });
    });
    $('#cart').on('click', '.removeCover', function(e) {
        $(this).parents('.item').remove();
        price=150;
        itemName="LaptopCover";
        $.ajax({
            url: "/deleteItem",
            method: "post",
            data: {"price":price,"item":itemName},

            success: function (data) {
                if(data==1)
                {
                    total=total-price;
                    $("#cartTotal").html(total);
                }
            }
        });
    });



    $("#placeOrder").on("click", function () {
        event.preventDefault();
        $.ajax({
            url: "/placeOrder",
            method: "post",
            success: function (data) {
                if(data==1)
                {
                 alert("order placed successfully!!");
                 location.reload(true);
                }
                else if(data==2)
                {
                alert("No product is added to cart");
                }
                else
                {
                    alert("We cannot place your order..Points insufficient!!");
                }
            }
        });
    });





});