<!DOCTYPE html>
<html>
<head>
  <%@include file="/common/taglib.jsp"%>
  <meta charset="UTF-8">
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0">
  <title>钉钉智能监控终端</title>
  
  <link rel="stylesheet" href="../../media/css/weui.min.css">
  <link rel="stylesheet" href="../../media/css/weui-gds.css">
  <link rel="stylesheet" href="../../media/jquery-weui/css/jquery-weui.css">
  
  <style type="text/css">
   .swiper-container {
     width: 100%;
     height: 230px;
   } 

  .swiper-container img {
    display: block;
    width: 100%;
  }
 </style>
</head>
<body ontouchstart>

<div class="container">
    
    <input type="hidden" name="warningID" id="warningID" value="${phoneEarlyWarningDTO.warningID }">
    
    <div class="weui-tab" style="height:auto;">
     <div class="weui-navbar">
        <div class="weui-navbar__item weui-bar__item_on">图集</div>
      </div> 
      
          <div class="weui-tab__panel">
          
               <div class="weui-cells">
		           <div class="weui-cell">
		               <div class="swiper-container">
		               
					      <div class="swiper-wrapper">
					        <c:choose>
					           <c:when test="${not empty phoneEarlyWarningDTO.imagespathList }">
					              <c:forEach items="${phoneEarlyWarningDTO.imagespathList }" var="imagespath">
							        <div class="swiper-slide"><img src="http://49.4.86.57:10083/file/${imagespath}" width="100%" height="194"/></div>
							      </c:forEach>
					           </c:when>
					           <c:otherwise>
					              <div class="swiper-slide"><img src="" /></div>
					           </c:otherwise> 
					        </c:choose>
					
                         </div>
					      <div class="swiper-pagination"></div>
					      
					    </div>
		           </div>
		           
		            <div class="weui-cell">
	                <a href="javascript:void(0);" id="openLocation"  latitude="${phoneEarlyWarningDTO.latitude }" longitude="${phoneEarlyWarningDTO.longitude }"  class="weui-media-box weui-media-box_appmsg">
                    <div class="weui-media-box__hd">
                        <img class="weui-media-box__thumb" src="../../media/images/map-marker.png" alt="">
                    </div>
                    <div class="weui-media-box__bd">
                        <h4 class="weui-media-box__title">${phoneEarlyWarningDTO.warningTypeName}告警地点</h4>
                        <p class="weui-media-box__desc">${phoneEarlyWarningDTO.warningAddress }</p>
                    </div>
                   </a>
	           </div>
	            
	            <div class="weui-cell" >
				    <c:if test="${phoneEarlyWarningDTO.warningStatusCode eq '1' }"></c:if>
				    <a href="javascript:void(0)" id="openDepContacts" class="weui-btn weui-btn_plain-default" style="margin-top: 0px;width:25%;font-size: 16px;">派警</a>
				    <a href="javascript:void(0)" class="weui-btn weui-btn_primary" onclick="modifyPhoneWarning('${phoneEarlyWarningDTO.warningID}','3');" style="margin-top: 0px;width:33%;font-size: 16px;">确认该警告</a>
					<a href="javascript:void(0)" id="shareAppMessage" class="weui-btn weui-btn_warnning"  onclick="modifyPhoneWarning('${phoneEarlyWarningDTO.warningID}','2');" style="margin-top: 0px;width:33%;font-size: 16px;" >标记为误报</a>
				</div>
		           
		  </div> 
		       
       </div>
             
   </div>
   
      
 </div>

  <script src="../../media/js/jquery.min.js"></script>
  <script src="../../media/jquery-weui/js/swiper.js"></script>
    <script src="../../media/jquery-weui/js/jquery-weui.js"></script>
  <script src="//g.alicdn.com/dingding/dingtalk-jsapi/2.0.57/dingtalk.open.js"></script>

  <script>
    
      $(".swiper-container").swiper({
        loop: false
      });
        
        configWx();
        function configWx() {  
        	// 获取当前页面URL链接
        	var  httpUrl = encodeURIComponent(location.href.split('#')[0]);
        	
        	$.ajax({
                cache: true, //缓存开启加速
                type: "POST",
                url: "/phoneWarngingDing/getDingJsTicket.do",
                data: "url="+httpUrl,
                async: false, //同步、异步                
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                  $.toast("JsTicket配置请求错误,重新打开！", function() {
                      console.log('close');
                    });
                },
                success: function(data) {
                	
                	if (data != null) {  
                     
                		dd.config({
                			agentId : data.agentId,
                			corpId : data.corpId,
                			timeStamp : data.timeStamp,
                			nonceStr : data.nonceStr,
                			signature : data.signature,
                			jsApiList : [ 'runtime.info', 
                			              'biz.contact.choose',
                					      'biz.util.previewImage',
                					      'biz.map.locate',
                					      'biz.util.openLink'
                					      ]
                		});
                		
                		dd.ready(function() {
                			 // dd.ready参数为回调函数，在环境准备就绪时触发，jsapi的调用需要保证在该回调函数触发后调用，否则无效。
                		    dd.runtime.permission.requestAuthCode({
                		        corpId: data.corpId,
                		        onSuccess: function(result) {
                		        },
                		        onFail : function(err) {}
                		    });
                			 
                			 
                			 // 点击图片 查看详细
                		    $(".swiper-container .swiper-wrapper img").on("click",function(){
                				var curSrc = $(this).attr("src");
                				var arrSrc = [];
                				for(var i = 0; i< $(".swiper-container .swiper-wrapper img").length;i++){
                					arrSrc.push($(".swiper-container .swiper-wrapper img").eq(i).attr("src"));
                				}
                				dd.biz.util.previewImage({
                				    urls: arrSrc,//图片地址列表
                				    current: curSrc,//当前显示的图片链接
                				    onSuccess : function(result) {
                				    },
                				    onFail : function(err) {}
                				})
                			});
                			 
                		     // 打开钉钉内置地图
                		    $("#openLocation").on("click", function() {
                		   		var latitude = parseFloat($(this).attr("latitude"));
                        	    var longitude = parseFloat($(this).attr("longitude"));
                				dd.biz.map.locate({
                					latitude: latitude, // 纬度，非必须
                					longitude: longitude, // 经度，非必须
                					onSuccess: function(result) {
                						
                					},
                					onFail: function(err) {}
                				})
                			});
                			
                			// 打开企业联系人
                			$("#openDepContacts").unbind("click").on("click", function() {
								dd.biz.contact.choose({
									startWithDepartmentId: -1, //-1表示打开的通讯录从自己所在部门开始展示, 0表示从企业最上层开始，(其他数字表示从该部门开始:暂时不支持)
									multiple: false, //是否多选： true多选 false单选； 默认true
									users: [], //默认选中的用户列表，userid；成功回调中应包含该信息
									corpId:data.corpId, //企业id
									onSuccess: function(data) {
										//onSuccess将在选人结束，点击确定按钮的时候被回调
										
									    //获取告警Id
                                        var  warningID = $("#warningID").val();
										// 发送消息
										selectMessage(warningID,data[0].emplId);
										
									},
									onFail: function(err) {
									//	alert(err);
									}
								});
							})
                			 
                			 
                			 
                		});
                		
                		dd.error(function(err) {
                			alert('dd error: ' + JSON.stringify(err));
                		});
                		
                		
                    } else {  
                 	 $.toast("JsTicket配置请求错误,重新打开！", function() {
                         console.log('close');
                     });
                  }  
             }
           });
     } 
        
      //  修改警告状态
      function modifyPhoneWarning(warningID,waringStatusCode){
    	  var  data = "waringStatusCode="+waringStatusCode+"&warningID="+warningID;
    	    $.ajax({
              cache: true, //缓存开启加速
              type: "POST",
              url: "/phoneWarningWeixin/modifyPhoneWarning.do",
              data: data,
              async: false, //同步、异步                
              error: function(XMLHttpRequest, textStatus, errorThrown) {
            	  $.toast("修改警告状态失败，请重试！", function() {
                      console.log('close');
                  });
              },
              success: function(data) {
              	  if (data != null) {  
              		  $.toast(data, function() {
                          console.log('close');
                      });
              	 } 
              }
      	}); 
      }
      
      
      //选择钉钉人员 ：点击确定
      var selectMessage = function(warningID,emplId) {
          console.log("warningID:"+warningID+",emplId:"+emplId);
           $.ajax({
              cache: true, //缓存开启加速
              type: "POST",
              url: "/phoneWarngingDing/sendDingActionCardMessage.do",
              data: "warningType=alarm&emplId="+emplId+"&warningID="+warningID,
              async: false, //同步、异步                
              error: function(XMLHttpRequest, textStatus, errorThrown) {
                $.toast("发送消息失败，请重试！", function() {
                       console.log('close');
                });
              },
              success: function(data) {
            	 var data  = eval('(' + data + ')');
               	 if (data != null) {
               		// 发送消息内容
             	    var messageData = "";
             	    if( 'success'  != data.message){
             	    	messageData = "发送消息失败！";
             	    }else{
             	    	messageData = "发送消息成功！";
             	    }
             	  
               	    $.toast(messageData, function() {
                       console.log(messageData);
                    });
               	  
               	 }
              }
      	}); 
      }
     
       //绘制图片中矩形：图片ID，画布ID，图片X坐标，图片y坐标，矩形宽度，矩形高度
      var creatCanvaFun = function(imgId,canvasId,x,y,width,height){
      		//新建img对象 
      		var img = new Image();
      		
      		//为新建的img赋值src
      		var mImg = document.getElementById(imgId);
      		img.src = mImg.getAttribute('src');
      		
      		//准备canvas
      		var canvas=document.getElementById(canvasId);
      		var context=canvas.getContext("2d");
      		  
      		//绘制图片
      		context.drawImage(img,0,0);
      		//绘制水印:用于填充绘画的颜色、渐变或模式,默认为无色
      		context.fillStyle = "rgba(0,0,255,0.3)";
      		//笔触的颜色
      		 context.strokeStyle = "red";
      		  //当前的线条宽度
      		context.lineWidth = 1;
      		  //绘制“被填充”的矩形
      		//context.fillRect(0,0, 0, 0);  
      		//绘制矩形（无填充）		   
      		context.strokeRect(x,y,width,height);
      }
      		
      //画图形
      //creatCanvaFun('img','myCanvas',347,140,55,100);
    </script>
</body>
</html>