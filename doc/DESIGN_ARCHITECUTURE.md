# client
# server

# server package 구조
# interface 의 분리 


### 서버 디자인
* 4개의 API를 서빙하기 위해서는 사실 전통적인 MVC layer로도 충분하다. 
  get, put은 단순하고 history, search는 레이어의 복잡도보다는 비지니스 로직이 복잡하기 때문이다.
  하지만, 프로토콜의 확장성,  
