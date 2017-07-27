# ItemSelector
使用方法：
    首先确定在project的build.gradle中添加kotlin支持（最好使用1.1.2-3版本）
    其次，项目开启DataBinding支持 在module的build.gradle添加
    
       dataBinding{
            enabled=true
        }
        
  然后在module中的dependency中添加如下：
  
          kapt 'com.android.databinding:compiler:2.3.3'
          compile "org.jetbrains.kotlin:kotlin-stdlib-jre7:1.1.2-3"
          
  Add it in your root build.gradle at the end of repositories:
  
        
        	allprojects {
        		repositories {
        			...
        			maven { url 'https://jitpack.io' }
        		}
        	}
Step 2. Add the dependency
        
        	dependencies {
        		compile 'com.github.x930073498:ItemSelector:0.0.5beta2'
        	}
  
        
