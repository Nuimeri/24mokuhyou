import './App.css'


//screens
import Default from './pages/Default'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import PrivateRoute from './pages/PrivateRoute';
import { useEffect, useState } from 'react';
import Login from './pages/Login';
import SignUp from './pages/SignUp';
import Home from './pages/Home';

function App() {
  const [isAuthenticated] = useState(false);
  // const [isAuthenticated, setIsAuthenticated] = useState(false);


  useEffect(() => {
    // ブラウザの言語を取得
    const language = navigator.language || "ja";
    
    // 初期言語を日本語に設定
    if (language.startsWith("ja")) {
      // 日本語の場合、何もせずにそのまま
      console.log("アプリは日本語モードです");
    } else {
      // 日本語以外の場合に適切な処理を追加する（任意）
      console.log("アプリは他の言語モードです");
    }
  }, []);

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />      
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/auth" element={<PrivateRoute element={<Default />} isAuthenticated={isAuthenticated} />} />
        <Route path="/*" element={<Default />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App;
