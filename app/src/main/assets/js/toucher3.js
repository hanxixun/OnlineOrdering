/**
 * @author ������
 * @github https://github.com/bh-lay/toucher
 * @modified 2015-6-23 00:25
 * 
 */

 
(function(global,doc,factoryFn){
	//��ʼ��toucher������
	var factory = factoryFn(global,doc);
	
	//�ṩwindow.util.toucher()�ӿ�
	global.util = global.util || {};
	global.util.toucher = global.util.toucher || factory;
	
	//�ṩCommonJS�淶�Ľӿ�
	global.define && define(function(require,exports,module){
		return factory;
	});
})(this,document,function(window,document){
	/**
	 * �ж��Ƿ�ӵ��ĳ��class
	 */
	function hasClass(dom,classSingle){
		return dom.className.match(new RegExp('(\\s|^)' + classSingle +'(\\s|$)'));
	}
	
	/**
	 * @method �¼�������
	 * @description �����¼���ԭʼ��������target��������׷���¼���
	 * 
	 * @param string �¼���
	 * @param object ԭ���¼�����
	 */
	function EMIT(eventName,e){
		this._events = this._events || {};
		//�¼����޸��¼�����������
		if(!this._events[eventName]){
			return;
		}
		//��¼��δ��ִ�е����¼���
		var rest_events = this._events[eventName];
		
		//���¼�Դ��target��ʼ����ð��
		var target = e.target;
		while (1) {
			//��û����Ҫִ�е��¼�������ð��
			if(rest_events.length ==0){
				return;
			}
			//���Ѿ�ð����������ⶥ���󶨣�����ð��
			if(target == this.dom || !target){
				//����ʣ�������¼���
				for(var i=0,total=rest_events.length;i<total;i++){
					var classStr = rest_events[i]['className'];
					var callback = rest_events[i]['fn'];
					//δָ���¼�ί�У�ֱ��ִ��
					if(classStr == null){
						event_callback(eventName,callback,target,e);
					}
				}
				return;
			}
			
			//��ǰ��ҪУ����¼���
			var eventsList = rest_events;
			//�ÿ���δִ�е����¼���
			rest_events = [];

			//�����¼����а�
			for(var i=0,total=eventsList.length;i<total;i++){
				var classStr = eventsList[i]['className'];
				var callback = eventsList[i]['fn'];
				//�����¼�ί�У�ִ��
				if(hasClass(target,classStr)){
					//����falseֹͣ�¼�ð�ݼ������¼����������ִ��
					if(event_callback(eventName,callback,target,e) == false){
						return;
					}
				}else{
					//������ִ��������ѹ�ص���δִ�е����б���
					rest_events.push(eventsList[i]);
				}
			}
			//����ð��
			target = target.parentNode;
		}
	}
	
	/**
	 * ִ�а󶨵Ļص�������������һ���¼�����
	 * @param[string]�¼���
	 * @param[function]��ִ�е��ĺ���
	 * @param[object]ָ���dom
	 * @param[object]ԭ��event����
	 */
	function event_callback(name,fn,dom,e){
		//����ʹ���Զ����touches��Ŀǰ��Ϊ�˽��touchEnd��touches�����⣩
		var touches = e.plugTouches || e.touches,
			touch = touches.length ? touches[0] : {},
			newE = {
				type : name,
				target : e.target,
				pageX : touch.pageX,
				pageY : touch.pageY,
				clientX : touch.clientX || 0,
				clientY : touch.clientY || 0
			};
		//Ϊswipe�¼����ӽ�����ʼλ�ü��ƶ�����
		if(name.match(/^swipe/) && e.plugStartPosition){
			newE.startX = e.plugStartPosition.pageX;
			newE.startY = e.plugStartPosition.pageY;
			newE.moveX = newE.pageX - newE.startX;
			newE.moveY = newE.pageY - newE.startY;
		}
		//ִ�а��¼��Ļص�������¼����ֵ
		var call_result = fn.call(dom,newE);
		//������false����ֹ�����Ĭ���¼�
		if(call_result == false){
			e.preventDefault();
			e.stopPropagation();
		}
		
		return call_result;
	}
	/**
	 * �ж�swipe����
	 */
	function swipeDirection(x1, x2, y1, y2) {
		return Math.abs(x1 - x2) >=	Math.abs(y1 - y2) ? (x1 - x2 > 0 ? 'Left' : 'Right') : (y1 - y2 > 0 ? 'Up' : 'Down');
	}

	/**
	 * ����ԭ�����¼�����������ģ���¼�
	 * 
	 */
	function eventListener(DOM){
		var this_touch = this;

		//�����ʼʱ��
		var touchStartTime = 0;
		
		//��¼��һ�ε��ʱ��
		var lastTouchTime = 0;
		
		//��¼��ʼ�����λ��
		var x1,y1,x2,y2;
		
		//����¼�����ʱ��
		var touchDelay;
		
		//���Գ����¼�����ʱ��
		var longTap;
		
		//��¼��ǰ�¼��Ƿ���Ϊ�ȴ�������״̬
		var isActive = false;
		//��¼��������Ϣ���¼�
		var eventMark = null;
		//�����û���������
		function actionOver(e){
			isActive = false;
			clearTimeout(longTap);
			clearTimeout(touchDelay);
		}
		
		//�϶��˴��¼�Ϊ����¼�
		function isSingleTap(){
			actionOver();
			EMIT.call(this_touch,'singleTap',eventMark);
		}
		//������ʼ
		function touchStart(e){
			//�����¼�
			eventMark = e;
			x1 = e.touches[0].pageX;
			y1 = e.touches[0].pageY;
			x2 = 0;
			y2 = 0;
			isActive = true;
			touchStartTime = new Date();
			EMIT.call(this_touch,'swipeStart',e);
			//����Ƿ�Ϊ����
			clearTimeout(longTap);
			longTap = setTimeout(function(){
				actionOver(e);
				//�϶��˴��¼�Ϊ�����¼�
				EMIT.call(this_touch,'longTap',e);
			},500);
		}
		//��������
		function touchend(e){
			//touchend�У��ò�������λ����Ϣ����ʹ��ȫ�ֱ���������
			e.plugStartPosition = eventMark.plugStartPosition;
			e.plugTouches = eventMark.touches;
			
			EMIT.call(this_touch,'swipeEnd',e);
			if(!isActive){
				return;
			}
			var now = new Date();
			//��δ����doubleTap��ֱ����Ӧ
			if(!this_touch._events.doubleTap || this_touch._events.doubleTap.length == 0){
				isSingleTap();
			}else if(now - lastTouchTime > 200){
				//�ӳ���Ӧ
				touchDelay = setTimeout(isSingleTap,190);
			}else{
				clearTimeout(touchDelay);
				actionOver(e);
				//�϶��˴��¼�Ϊ������������¼�
				EMIT.call(this_touch,'doubleTap',eventMark);
			}
			lastTouchTime = now;
		}
		
		//��ָ�ƶ�
		function touchmove(e){
			//�����¼�
			eventMark = e;
			//��ԭ���¼������ϼ�¼��ʼλ�ã�Ϊswipe�¼����Ӳ������ݣ�
			e.plugStartPosition = {
				pageX : x1,
				pageY : y1
			};
			//�϶��˴��¼�Ϊ�ƶ��¼�
			EMIT.call(this_touch,'swipe',e);

			if(!isActive){
				return;
			}
			x2 = e.touches[0].pageX;
			y2 = e.touches[0].pageY;
			if(Math.abs(x1-x2)>2 || Math.abs(y1-y2)>2){
				//�϶��˴��¼�Ϊ�ƶ�����
				var direction = swipeDirection(x1, x2, y1, y2);
				EMIT.call(this_touch,'swipe' + direction,e);
			}else{
				//�϶��˴��¼�Ϊ����¼�
				isSingleTap();
			}
			actionOver(e);
		}

		/**
		 * �Կ�ʼ���Ƶļ���
		 */
		DOM.addEventListener('touchstart',touchStart);
		DOM.addEventListener('MSPointerDown',touchStart);
		DOM.addEventListener('pointerdown',touchStart);

		/**
		 * �����ƽ����ļ����������
		 */
		DOM.addEventListener('touchend',touchend);
		DOM.addEventListener('MSPointerUp',touchend);
		DOM.addEventListener('pointerup',touchend);

		/**
		 * ���ƶ����Ƶļ���
		 */
		DOM.addEventListener('touchmove',touchmove);
		DOM.addEventListener('MSPointerMove',touchmove);
		DOM.addEventListener('pointermove',touchmove);

		/**
		 * ���ƶ������ļ���
		 */
		DOM.addEventListener('touchcancel',actionOver);
		DOM.addEventListener('MSPointerCancel',actionOver);
		DOM.addEventListener('pointercancel',actionOver);
	}
	
	/**
	 * touch��
	 * 
	 */
	function Touch(DOM,param){
		var param = param || {};

		this.dom = DOM;
		//�洢�����¼��Ļص�
		this._events = {};
		//����DOMԭ���¼�
		eventListener.call(this,this.dom);
	}
	/**
	 * @method �����¼�����
	 * @description ֧����ʽ����
	 * 
	 * @param string �¼���
	 * @param [string] �¼�ί����ĳ��class����ѡ��
	 * @param function �����������¼�������ʱ��Ҫִ�еĻص����� 
	 * 
	 **/
	Touch.prototype.on = function ON(eventStr,a,b){
		var className,fn;
		if(typeof(a) == 'string'){
			className = a.replace(/^\./,'');
			fn = b;
		}else{
			className = null;
			fn = a;
		}
		//���callback�Ƿ�Ϸ�,�¼��������Ƿ���ڡ�
		if(typeof(fn) == 'function' && eventStr && eventStr.length){
			var eventNames = eventStr.split(/\s+/);
			for(var i=0,total=eventNames.length;i<total;i++){
			
				var eventName = eventNames[i];
				//�¼����޸��¼�������һ���¼���
				if(!this._events[eventName]){
					this._events[eventName] = [];
				}
				this._events[eventName].push({
					className : className,
					fn : fn
				});
			}
		}
		
		//�ṩ��ʽ���õ�֧��
		return this;
	};
	
	//�����ṩ�ӿ�
	return function (dom){
		return new Touch(dom);
	};
});