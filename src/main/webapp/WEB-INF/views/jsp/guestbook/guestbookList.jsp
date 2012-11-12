<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="This is description" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><spring:message code="blog.label.guestbook"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link href="${root}/common/css/bootstrap/bootstrap.css" rel="stylesheet">
<link href="${root}/common/css/bootstrap/bootstrap.css" rel="stylesheet">
<link href="${root}/common/css/bootstrap/bootstrap-responsive.css" rel="stylesheet">
<link href="${root}/common/css/jquery/jquery-ui-1.8.16.custom.css" rel="stylesheet">
<link href="${root}/common/css/common.css" rel="stylesheet">

<script type="text/javascript" src="${root}/common/js/jquery/jquery-1.7.1.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${root}/common/js/common.js"></script>
<!-- JS Lib -->
<script type="text/javascript" src="${root}/common/js/lib/json2.js"></script>
<script type="text/javascript" src="${root}/common/js/lib/underscore.js"></script>
<script type="text/javascript" src="${root}/common/js/lib/backbone.js"></script>

<!-- 
<script src="http://ajax.cdnjs.com/ajax/libs/json2/20110223/json2.js"></script>
<script src="http://ajax.cdnjs.com/ajax/libs/underscore.js/1.1.6/underscore-min.js"></script>
<script src="http://ajax.cdnjs.com/ajax/libs/backbone.js/0.3.3/backbone-min.js"></script>
 -->

<script type="text/javascript">
    $(document).ready(function() {
        $('.active').removeClass();
        $('#guestbookMenu').addClass('active');
        
        (function($){
            Backbone.sync = function(method, model, success, error){ 
              success();
            }
            
            var Item = Backbone.Model.extend({
              defaults: {
                part1: 'hello',
                part2: 'world'
              }
            });
            
            var List = Backbone.Collection.extend({
              model: Item
            });

            var ItemView = Backbone.View.extend({
              events: { 
                'click span.swap':  'swap',
                'click span.delete': 'remove'
              },    
              initialize: function(){
                _.bindAll(this, 'render', 'unrender', 'swap', 'remove'); // every function that uses 'this' as the current object should be in here

                this.model.bind('change', this.render);
                this.model.bind('remove', this.unrender);
              },
              render: function(){
                $(this.el).html('<span style="color:black;">'+this.model.get('part1')+' '+this.model.get('part2')+'</span> &nbsp; &nbsp; <span class="swap" style="font-family:sans-serif; color:blue; cursor:pointer;">[swap]</span> <span class="delete" style="cursor:pointer; color:red; font-family:sans-serif;">[delete]</span>');
                return this; // for chainable calls, like .render().el
              },
              unrender: function(){
                $(this.el).remove();
              },
              swap: function(){
                var swapped = {
                  part1: this.model.get('part2'), 
                  part2: this.model.get('part1')
                };
                this.model.set(swapped);
              },
              remove: function(){
                this.model.destroy();
              }
            });
            var ListView = Backbone.View.extend({
              el: $('body'), // el attaches to existing element
              events: {
                'click button#add': 'addItem'
              },
              initialize: function(){
                _.bindAll(this, 'render', 'addItem', 'appendItem'); // every function that uses 'this' as the current object should be in here
                
                this.collection = new List();
                this.collection.bind('add', this.appendItem); // collection event binder

                this.counter = 0;
                this.render();
              },
              render: function(){
                var self = this;
                $(this.el).append("<button id='add'>Add list item</button>");
                $(this.el).append("<ul></ul>");
                _(this.collection.models).each(function(item){ // in case collection is not empty
                  self.appendItem(item);
                }, this);
              },
              addItem: function(){
                this.counter++;
                var item = new Item();
                item.set({
                  part2: item.get('part2') + this.counter // modify item defaults
                });
                this.collection.add(item);
              },
              appendItem: function(item){
                var itemView = new ItemView({
                  model: item
                });
                $('ul', this.el).append(itemView.render().el);
              }
            });

            var listView = new ListView();
          })(jQuery);
          
    });
    

</script>
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
</head>
<body>
    <!-- 상단 hearder 영역 -->
    <%@ include file="/WEB-INF/views/jsp/layout/header.jsp" %>

    <!-- contents 영역 -->
    <div class="container">
        <fieldset>
            <legend><strong><spring:message code="blog.label.guestbook"/></strong></legend>

           
        </fieldset>
     
        <!-- 하단 footer 영역 -->   
       
    </div>
    <!-- /container -->

    <!-- common html include -->
    <%@ include file="/WEB-INF/views/jsp/common/commonHtml.jsp" %>

    <!-- common file include -->
    <%@ include file="/WEB-INF/views/jsp/common/include.jsp" %>
</body>
</html>