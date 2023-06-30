package com.lb.common;

public enum ResultCode {
	
	

	 SUCCESS(200, "成功"),

	    /* 参数错误：10001explain 19999 */
	    /**
	     * explain 参数无效
	     * 
	     * @author Jiaojiao He
	     */
	   Fail(400, "失败");
	
	private Integer code;
	private String msg;


		private ResultCode(Integer code, String msg) {
			this.code = code;
			this.msg = msg;
		}


		public Integer getCode() {
			return code;
		}


		public void setCode(Integer code) {
			this.code = code;
		}


		public String getMsg() {
			return msg;
		}


		public void setMsg(String msg) {
			this.msg = msg;
		}
		
		
}
