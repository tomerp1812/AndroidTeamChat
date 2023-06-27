import Button from './Button/Button';
import BigBlock from './BigBlock/BigBlock';

//main chat page
function Chat({ user }) {
  return (
    <>
      <Button me={user}/>
      <BigBlock me={user} />
    </>
  );
}

export default Chat;
