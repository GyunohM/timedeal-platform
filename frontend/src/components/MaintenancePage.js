import React from 'react';

const MaintenancePage = () => {
  return (
    <div style={styles.container}>
      <div style={styles.content}>
        <h1 style={styles.title}>🚧 서비스 점검 중</h1>
        <p style={styles.text}>
          안녕하세요, <strong>동네콕</strong>입니다.<br />
          현재 서버 비용 절감을 위해 잠시 서비스가 중단된 상태입니다.
        </p>
        <p style={styles.subText}>
          (개발자가 AWS EC2 인스턴스를 꺼두었습니다.)
        </p>
        <div style={styles.divider}></div>
        <a
          href="https://github.com/maenggyuno/timedeal-platform-docs/blob/main/README.md"
          target="_blank"
          rel="noopener noreferrer"
          style={styles.button}
        >
          GitHub 포트폴리오 보러가기
        </a>
      </div>
    </div>
  );
};

// 간단한 스타일 (CSS 파일로 빼셔도 됩니다)
const styles = {
  container: {
    display: 'flex', justifyContent: 'center', alignItems: 'center',
    height: '100vh', backgroundColor: '#f8f9fa',
  },
  content: {
    textAlign: 'center', padding: '40px', backgroundColor: 'white',
    borderRadius: '15px', boxShadow: '0 4px 20px rgba(0,0,0,0.1)',
    maxWidth: '500px', width: '90%',
  },
  title: { color: '#ff6b6b', marginBottom: '20px', fontSize: '24px' },
  text: { color: '#333', lineHeight: '1.6', marginBottom: '10px' },
  subText: { color: '#888', fontSize: '14px', marginBottom: '30px' },
  divider: { height: '1px', backgroundColor: '#eee', margin: '20px 0' },
  button: {
    display: 'inline-block', padding: '12px 24px', backgroundColor: '#333',
    color: 'white', textDecoration: 'none', borderRadius: '8px', fontWeight: 'bold',
  }
};

export default MaintenancePage;
