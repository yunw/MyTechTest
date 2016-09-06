package com.test.example.network.jenkins.model;

import java.util.List;

public class JenkinsResult {
	
	@SuppressWarnings("unused")
    private List<Action> actions;
	
	class Action {
		
		class BuildsByBranchName {
			
		}
		
		class Cause {
			
			private String shortDescription;
			
			private String userId;
			
			private String userName;

			public String getShortDescription() {
				return shortDescription;
			}

			public void setShortDescription(String shortDescription) {
				this.shortDescription = shortDescription;
			}

			public String getUserId() {
				return userId;
			}

			public void setUserId(String userId) {
				this.userId = userId;
			}

			public String getUserName() {
				return userName;
			}

			public void setUserName(String userName) {
				this.userName = userName;
			}
			
		}
		
		class Parameter {
			
			private String name;
			
			private String value;

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getValue() {
				return value;
			}

			public void setValue(String value) {
				this.value = value;
			}
			
		}

	}

}
