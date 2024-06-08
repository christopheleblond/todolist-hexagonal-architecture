import logo from './logo.svg';
import './App.css';
import TodoLists from './TodoLists';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        {TodoLists()}
      </header>
    </div>
  );
}

export default App;
